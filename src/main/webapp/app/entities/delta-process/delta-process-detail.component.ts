import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessService } from 'app/entities/delta-process/delta-process.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { callbackify } from 'util';
import { sample } from 'rxjs/operators';
import { Validators } from '@angular/forms';

@Component({
  selector: 'jhi-delta-process-detail',
  templateUrl: './delta-process-detail.component.html',
  styleUrls: ['delta-process.scss']
})
export class DeltaProcessDetailComponent implements OnInit {
  deltaProcess: IDeltaProcess;
  tables: any;
  tablesCopy: any;
  selectedTables = [];
  isSaving: boolean;
  masterSelected: boolean;
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected deltaProcessService: DeltaProcessService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deltaProcess }) => {
      this.deltaProcess = deltaProcess;
      this.selectedTables = this.deltaProcess.tablesList ? JSON.parse(this.deltaProcess.tablesList) : [];
      this.getTableList();
      this.masterSelected = false;
    });
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }

  getTableList() {
    this.deltaProcessService.getTableList(this.deltaProcess).subscribe(response => {
      // this.tables = this.tablesCopy = response.body;
      this.prepareData(response.body);
    });
  }

  prepareData(response) {
    this.tables = [];
    response.tableinfo.forEach(element => {
      const table = {
        name: element.tableName,
        primaryKey: element.PrimaryKey
      };
      this.tables.push(table);
    });
    this.tablesCopy = this.tables;
    this.isAllSelected();
  }

  checkUncheckAll() {
    console.log(this.tables);
    this.selectedTables = [];
    for (let i = 0; i < this.tables.length; i++) {
      this.tables[i].selected = this.masterSelected;
      this.pushTables(this.tables[i]);
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
    const PrimaryKey = [];
    this.tables.forEach(element => {
      if (element.selected) {
        PrimaryKey.push(element.name);
        PrimaryKey.push(element.primaryKey);
      }
    });
    this.deltaProcess.tablesList = JSON.stringify(this.selectedTables);
    this.deltaProcess.Pk = PrimaryKey ? JSON.stringify(PrimaryKey) : null;
    console.log(this.deltaProcess);
    this.subscribeToSaveResponse(this.deltaProcessService.update(this.deltaProcess));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeltaProcess>>) {
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
