import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { callbackify } from 'util';
import { sample } from 'rxjs/operators';
import { Validators } from '@angular/forms';

@Component({
  selector: 'jhi-migration-process-detail',
  templateUrl: './migration-process-detail.component.html',
  styleUrls: ['migration-process.scss']
})
export class MigrationProcessDetailComponent implements OnInit {
  migrationProcess: IMigrationProcess;
  tables: any;
  tablesCopy: any;
  selectedTables = [];
  cdcTables = [];
  bulkTables = [];
  isSaving: boolean;
  masterSelected: boolean;
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected migrationProcessService: MigrationProcessService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
      this.bulkTables = this.migrationProcess.bulk ? JSON.parse(this.migrationProcess.bulk) : [];
      this.cdcTables = this.migrationProcess.cdc ? JSON.parse(this.migrationProcess.cdc) : [];
      this.selectedTables = this.migrationProcess.tablesToMigrate ? JSON.parse(this.migrationProcess.tablesToMigrate) : [];
      this.getTableList();
      this.masterSelected = false;
    });
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }

  getTableList() {
    this.migrationProcessService.getTableList(this.migrationProcess).subscribe(response => {
      // this.tables = this.tablesCopy = response.body;
      this.prepareData(response.body);
    });
  }

  prepareData(response) {
    this.tables = [];
    response.tableinfo.forEach(element => {
      const table = {
        name: element.tableName,
        primaryKey: element.PrimaryKey,
        selected: this.isChecked(element.tableName),
        cdc: this.isCDC(element.tableName),
        cdcColumnList: element.cdcColumnList,
        selectedCdcCol: element.cdcColumnList[0]
      };
      this.tables.push(table);
    });
    this.tablesCopy = this.tables;
    this.isAllSelected();
  }

  checkUncheckAll(event) {
    if (event.target.checked) {
      this.selectedTables = [];
      for (let i = 0; i < this.tables.length; i++) {
        this.tables[i].selected = this.masterSelected;
        this.pushTables(this.tables[i]);
      }
    } else {
      this.selectedTables = [];
      for (let i = 0; i < this.tables.length; i++) {
        this.tables[i].selected = this.masterSelected;
      }
    }
  }

  isAllSelected() {
    this.masterSelected = this.tables.every(function(item: any) {
      return item.selected === true;
    });
  }

  onSelectionChange(item) {
    this.isAllSelected();
    this.pushTables(item);
  }

  selectPk(id, name) {
    this.router.navigate(['/migration-process', id, 'view', name, 'selectPK']);
  }

  pushTables(item) {
    const index = this.selectedTables.indexOf(item.name);
    if (index === -1) {
      // val not found, pushing onto array
      this.selectedTables.push(item.name);
    } else {
      // val is found, removing from array
      this.selectedTables.splice(index, 1);
    }
  }

  onProcessSelection(item) {
    item.cdc = item.cdc ? false : true;
  }

  isChecked(item) {
    const index = this.selectedTables.indexOf(item);
    if (index === -1) {
      return false;
    } else {
      return true;
    }
  }

  isCDC(item) {
    const index = this.cdcTables.indexOf(item);
    if (index === -1) {
      return false;
    } else {
      // item.bulk = true;
      return true;
    }
  }

  customSwitchId(i) {
    return 'customSwitch' + i;
  }

  searchForTables(val) {
    if (val) {
      this.tables = this.tablesCopy.filter(option => {
        return option.name.toLowerCase().includes(val.toLowerCase());
      });
      return this.tables;
    } else {
      this.tables = this.tablesCopy;
    }
  }

  testAndMigrate() {
    const bulk = [];
    const cdc = [];
    const cdcColumns = [],
      cdcPrimaryKey = [],
      bulkPrimaryKey = [];
    this.tables.forEach(element => {
      if (element.selected) {
        if (element.cdc) {
          cdc.push(element.name);
          cdcColumns.push(element.selectedCdcCol);
          cdcPrimaryKey.push(element.primaryKey);
        } else {
          bulk.push(element.name);
          bulkPrimaryKey.push(element.primaryKey);
        }
      }
    });
    this.migrationProcess.tablesToMigrate = JSON.stringify(this.selectedTables);
    this.migrationProcess.cdc = cdc ? JSON.stringify(cdc) : null;
    this.migrationProcess.bulk = bulk ? JSON.stringify(bulk) : null;
    this.migrationProcess.cdcPk = cdcPrimaryKey ? JSON.stringify(cdcPrimaryKey) : null;
    this.migrationProcess.bulkPk = bulkPrimaryKey ? JSON.stringify(bulkPrimaryKey) : null;
    this.migrationProcess.cdcCols = cdcColumns ? JSON.stringify(cdcColumns) : null;
    console.log(this.migrationProcess.cdcCols);
    this.subscribeToSaveResponse(this.migrationProcessService.update(this.migrationProcess));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMigrationProcess>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
