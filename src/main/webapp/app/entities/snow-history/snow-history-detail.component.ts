import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISnowHistory } from 'app/shared/model/snow-history.model';
import { SnowHistoryService } from 'app/entities/snow-history/snow-history.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { callbackify } from 'util';
import { sample } from 'rxjs/operators';
import { Validators } from '@angular/forms';

@Component({
  selector: 'jhi-snow-history-detail',
  templateUrl: './snow-history-detail.component.html',
  styleUrls: ['snow-history.scss']
})
export class SnowHistoryDetailComponent implements OnInit {
  snowHistory: ISnowHistory;
  tables: any;
  tablesCopy: any;
  selectedTables = [];
  //   cdcTables = [];
  //   bulkTables = [];
  isSaving: boolean;
  masterSelected: boolean;
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected snowHistoryService: SnowHistoryService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowHistory }) => {
      this.snowHistory = snowHistory;
      //   this.bulkTables = this.snowHistory.bulk ? JSON.parse(this.snowHistory.bulk) : [];
      //   this.cdcTables = this.snowHistory.cdc ? JSON.parse(this.snowHistory.cdc) : [];
      this.selectedTables = this.snowHistory.tablesToMigrate ? JSON.parse(this.snowHistory.tablesToMigrate) : [];
      this.getTableList();
      this.masterSelected = false;
    });
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }

  getTableList() {
    this.snowHistoryService.getTableList(this.snowHistory).subscribe(response => {
      // this.tables = this.tablesCopy = response.body;
      this.prepareData(response.body);
    });
  }

  prepareData(response) {
    this.tables = [];
    response.tableinfo.forEach(element => {
      const table = {
        name: element.tableName,
        // primaryKey: element.PrimaryKey,
        selected: this.isChecked(element.tableName)
        // cdc: this.isCDC(element.tableName),
        // cdcColumnList: element.cdcColumnList,
        // selectedCdcCol: element.selectedCdcCol
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

  reset() {
    this.ngOnInit();
  }

  onSelectionChange(item) {
    this.isAllSelected();
    this.pushTables(item);
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

  //   onProcessSelection(item) {
  //     item.cdc = item.cdc ? false : true;
  //   }

  isChecked(item) {
    const index = this.selectedTables.indexOf(item);
    if (index === -1) {
      return false;
    } else {
      return true;
    }
  }

  //   isCDC(item) {
  //     const index = this.cdcTables.indexOf(item);
  //     if (index === -1) {
  //       return false;
  //     } else {
  //       // item.bulk = true;
  //       return true;
  //     }
  //   }

  //   customSwitchId(i) {
  //     return 'customSwitch' + i;
  //   }

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
    //     const bulk = [];
    //     const cdc = [];
    //     const cdcColumns = [],
    //       cdcPrimaryKey = [],
    //       bulkPrimaryKey = [];
    //     this.tables.forEach(element => {
    //       if (element.selected) {
    //         if (element.cdc) {
    //           cdc.push(element.name);
    //           cdcColumns.push(element.selectedCdcCol);
    //           cdcPrimaryKey.push(element.primaryKey);
    //         } else {
    //           bulk.push(element.name);
    //           bulkPrimaryKey.push(element.primaryKey);
    //         }
    //       }
    //     });
    this.snowHistory.tablesToMigrate = JSON.stringify(this.selectedTables);
    // this.snowHistory.cdc = cdc ? JSON.stringify(cdc) : null;
    // this.snowHistory.bulk = bulk ? JSON.stringify(bulk) : null;
    // this.snowHistory.cdcPk = cdcPrimaryKey ? JSON.stringify(cdcPrimaryKey) : null;
    // this.snowHistory.bulkPk = bulkPrimaryKey ? JSON.stringify(bulkPrimaryKey) : null;
    // this.snowHistory.cdcCols = cdcColumns ? JSON.stringify(cdcColumns) : null;
    // console.log(this.snowHistory.cdcCols);
    this.subscribeToSaveResponse(this.snowHistoryService.update(this.snowHistory));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISnowHistory>>) {
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
