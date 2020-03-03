import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IDeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessService } from 'app/entities/delta-process/delta-process.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-delta-process-detail',
  templateUrl: './delta-process-detail.component.html',
  styleUrls: ['delta-process.scss']
})
export class DeltaProcessDetailComponent implements OnInit {
  deltaProcess: IDeltaProcess;
  tables: any;
  columns: any;
  closeResult: string;
  tablesCopy: any;
  selectedTables = [];
  selectedColumns: any[];
  selectedColumnsBuffer: any[];
  isSaving: boolean;
  save_disable: boolean;
  masterSelected: boolean;
  fileFlag: boolean;
  check: any[];
  str: string;
  tb = [];
  add: any;
  deltaspin: boolean;
  delmodspin: boolean;
  delfilemodspin: boolean;
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected deltaProcessService: DeltaProcessService,
    protected router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.deltaspin = true;
    this.activatedRoute.data.subscribe(({ deltaProcess }) => {
      this.deltaProcess = deltaProcess;
      this.fileFlag = deltaProcess.sourceType === 'Flatfiles' ? true : false;
      this.selectedTables = this.deltaProcess.tablesList ? JSON.parse(this.deltaProcess.tablesList) : [];
      this.selectedColumns = this.deltaProcess.selectedColumns ? JSON.parse(this.deltaProcess.selectedColumns) : [];
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
      this.deltaspin = false;
    });
  }

  getFileColumn(fileName) {
    this.deltaProcessService.getFileColumnList(this.deltaProcess, fileName).subscribe(response => {
      this.prepareColumn(fileName, response.body);
      this.delfilemodspin = false;
    });
  }
  prepareData(response) {
    this.tables = [];
    response.tableinfo.forEach(element => {
      const table = {
        name: element.tableName,
        primaryKey: element.PrimaryKey,
        selected: this.isChecked(element.tableName),
        columnList: element.columnList
      };
      this.setPK(table.name, table.primaryKey);
      this.tables.push(table);
    });
    this.tablesCopy = this.tables;
    this.isAllSelected();
  }

  prepareColumn(tableName, columnList) {
    this.columns = [];
    columnList.forEach(columnItem => {
      const column = {
        columnName: columnItem,
        selected: this.isCheckedPK(tableName, columnItem)
      };
      this.columns.push(column);
    });
    this.delmodspin = false;
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

  onPKSelection(tableName, column) {
    // column.selected = !column.selected;
    let columnName = column.columnName.split('-');
    columnName = columnName + '-' + tableName;
    const index = this.selectedColumns.indexOf(columnName);
    if (index === -1) {
      // val not found, pushing onto array
      this.selectedColumns.push(columnName);
    } else {
      // val is found, removing from array
      this.selectedColumns.splice(index, 1);
    }
  }
  selectPK(item, content) {
    this.modalService.open(content);
    this.delmodspin = true;
    this.prepareColumn(item.name, item.columnList);
  }

  selectFilePK(item, content) {
    this.columns = [];
    this.modalService.open(content);
    this.delfilemodspin = true;
    this.getFileColumn(item.name);
  }

  searchStringInArray(a, b) {
    for (let j = 0; j < b.length; j++) {
      if (b[j].match(a)) {
        return j;
      }
    }
    return -1;
    console.log('return' + -1);
  }

  setPK(tableName, PK) {
    this.selectedColumns.forEach(element => {
      this.tb.push(element.split('-')[1]);
    });
    this.add = this.searchStringInArray(tableName, this.tb);
    if (PK !== null && PK !== '') {
      PK = PK.split('-');
      for (const pkCol of PK) {
        const columnName = pkCol + '-' + tableName;
        const index = this.selectedColumns.indexOf(columnName);
        if (this.add === -1 && index === -1) {
          this.selectedColumns.push(columnName);
        }
      }
    }
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

  isCheckedPK(tableName, column) {
    column = column + '-' + tableName;
    const index = this.selectedColumns.indexOf(column);
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

  reset() {
    this.ngOnInit();
  }

  testAndMigrate() {
    const PrimaryKey = [];
    this.tables.forEach(element => {
      if (element.selected) {
        // PrimaryKey.push(element.name);
        // PrimaryKey.push(element.primaryKey);
        let firstFlag = true;
        let pkList = '';
        this.selectedColumns.forEach(column => {
          const columnSplit = column.split('-');
          if (element.name === columnSplit[1] && firstFlag) {
            pkList = pkList + columnSplit[0];
            if (pkList === '') {
              this.save_disable = true;
            } else {
              this.save_disable = false;
            }
            firstFlag = false;
          } else if (element.name === columnSplit[1] && !firstFlag) {
            pkList = pkList + '-' + columnSplit[0];
            if (pkList === '') {
              this.save_disable = true;
            } else {
              this.save_disable = false;
            }
          }
        });
        PrimaryKey.push(pkList);
        if (pkList === '') {
          this.save_disable = true;
        } else {
          this.save_disable = false;
        }
      }
    });
    if (this.save_disable) {
      const smsg = 'snowpoleApp.deltaProcess.primarykeyvalidation';
      this.jhiAlertService.error(smsg);
    } else {
      this.deltaProcess.selectedColumns = JSON.stringify(this.selectedColumns);
      this.deltaProcess.tablesList = JSON.stringify(this.selectedTables);
      this.deltaProcess.pk = PrimaryKey ? JSON.stringify(PrimaryKey) : null;
      this.subscribeToSaveResponse(this.deltaProcessService.update(this.deltaProcess));
    }
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
