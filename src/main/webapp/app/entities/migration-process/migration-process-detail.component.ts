import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { callbackify } from 'util';
import { sample } from 'rxjs/operators';

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
  cdcPrimaryKey = [];
  bulkPrimaryKey = [];
  isSaving: boolean;
  cdcColumns = [];
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected migrationProcessService: MigrationProcessService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
      this.bulkTables = this.migrationProcess.bulk ? JSON.parse(this.migrationProcess.bulk) : [];
      this.cdcTables = this.migrationProcess.cdc ? JSON.parse(this.migrationProcess.cdc) : [];
      this.selectedTables = this.migrationProcess.tablesToMigrate ? JSON.parse(this.migrationProcess.tablesToMigrate) : [];
      this.cdcColumns = this.migrationProcess.cdcColumns ? JSON.parse(this.migrationProcess.cdcColumns) : [];
      this.cdcPrimaryKey = this.migrationProcess.cdcPrimaryKey ? JSON.parse(this.migrationProcess.cdcPrimaryKey) : [];
      this.bulkPrimaryKey = this.migrationProcess.bulkPrimaryKey ? JSON.parse(this.migrationProcess.bulkPrimaryKey) : [];
      this.getTableList();
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
        cdcColumnList: element.cdcColumnList
      };
      this.tables.push(table);
    });
    this.tablesCopy = this.tables;
  }
  // for (let i=0; i<response.tableinfo.length; i++){
  //   const tabName: string[] = response.tableinfo[i].tableName;
  //   const pk: string[] = response.tableinfo[i].primaryKey;
  //   const table =  { name: tabName , primaryKey: pk ,selected: this.isChecked(tabName), cdc: this.isCDC(tabName) };
  //   this.tables.push(table);
  // };
  // console.log(copy);
  // let sam:string = copy.tableName.

  onSelectionChange(item) {
    item.selected = !item.selected;
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
      // item.selected = true;
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
    const cdcColumns = [];
    this.tables.forEach(element => {
      if (element.selected) {
        if (element.cdc) {
          cdc.push(element.name);
          this.cdcColumns.push(element.cdcColumnList);
          this.cdcPrimaryKey.push(element.primaryKey);
        } else {
          bulk.push(element.name);
          this.bulkPrimaryKey.push(element.primaryKey);
        }
      }
    });
    this.migrationProcess.tablesToMigrate = JSON.stringify(this.selectedTables);
    this.migrationProcess.cdc = cdc ? JSON.stringify(cdc) : null;
    this.migrationProcess.bulk = bulk ? JSON.stringify(bulk) : null;
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
