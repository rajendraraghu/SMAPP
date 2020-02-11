import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-migration-process-detail',
  templateUrl: './migration-process-detail.component.html',
  styleUrls: ['migration-process.scss']
})
export class MigrationProcessDetailComponent implements OnInit {
  migrationProcess: IMigrationProcess;
  tables: any;
  discdc: boolean;
  type: string;
  columns: any;
  closeResult: string;
  tablesCopy: any;
  selectedTables = [];
  selectedColumns: any[];
  selectedColumnsBuffer: any[];
  cdcTables = [];
  bulkTables = [];
  isSaving: boolean;
  masterSelected: boolean;
  check: any[];
  str: string;
  tb = [];
  add: any;
  tableName: string;
  showSpinner: true;
  // save_disable: string[];
  disable: boolean;
  commonspin: boolean;
  tablemodalspin: boolean;
  filemodalspin: boolean;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected migrationProcessService: MigrationProcessService,
    protected router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.commonspin = true;
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
      this.type = migrationProcess.sourceType;
      if (this.type === 'Flatfiles') {
        this.discdc = false;
      } else {
        this.discdc = true;
      }
      this.bulkTables = this.migrationProcess.bulk ? JSON.parse(this.migrationProcess.bulk) : [];
      this.cdcTables = this.migrationProcess.cdc ? JSON.parse(this.migrationProcess.cdc) : [];
      this.selectedTables = this.migrationProcess.tablesToMigrate ? JSON.parse(this.migrationProcess.tablesToMigrate) : [];
      this.selectedColumns = this.migrationProcess.selectedColumns ? JSON.parse(this.migrationProcess.selectedColumns) : [];
      this.getTableList();
      this.masterSelected = false;
      // this.flag = false;
    });
    this.isSaving = false;
  }

  previousState() {
    window.history.back();
  }

  getTableList() {
    this.migrationProcessService.getTableList(this.migrationProcess).subscribe(response => {
      this.prepareData(response.body);
      this.commonspin = false;
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
        selectedCdcCol: element.cdcColumnList[0],
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
    this.tablemodalspin = false;
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
    this.columns = [];
    this.modalService.open(content);
    this.tablemodalspin = true;
    this.prepareColumn(item.name, item.columnList);
  }

  selectFilePK(item, content) {
    this.columns = [];
    this.modalService.open(content);
    this.filemodalspin = true;
    this.getFileColumn(item.name);
  }

  getFileColumn(fileName) {
    this.migrationProcessService.getFileColumnList(this.migrationProcess, fileName).subscribe(response => {
      this.prepareColumn(fileName, response.body);
      this.filemodalspin = false;
    });
  }

  searchStringInArray(a, b) {
    for (let j = 0; j < b.length; j++) {
      if (b[j].match(a)) {
        return j;
      }
    }
    return -1;
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
  reset() {
    this.ngOnInit();
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
    const save_disable = [];
    const cdcColumns = [],
      cdcPrimaryKey = [],
      bulkPrimaryKey = [];
    this.disable = false;
    if (this.selectedTables.length > 0) {
      this.tables.forEach(element => {
        // this.save_disable = false;
        if (element.selected) {
          if (element.cdc) {
            cdc.push(element.name);
            cdcColumns.push(element.selectedCdcCol);
            let firstFlag = true;
            let pkList = '';
            this.selectedColumns.forEach(column => {
              const columnSplit = column.split('-');
              if (element.name === columnSplit[1] && firstFlag) {
                pkList = pkList + columnSplit[0];
                firstFlag = false;
              } else if (element.name === columnSplit[1] && !firstFlag) {
                pkList = pkList + '-' + columnSplit[0];
              }
            });
            if (pkList === '') {
              save_disable.push(true);
            } else {
              save_disable.push(false);
              cdcPrimaryKey.push(pkList);
            }
          } else {
            bulk.push(element.name);
            let firstFlag = true;
            let pkList = '';
            this.selectedColumns.forEach(column => {
              const columnSplit = column.split('-');
              if (element.name === columnSplit[1] && firstFlag) {
                pkList = pkList + columnSplit[0];
                firstFlag = false;
              } else if (element.name === columnSplit[1] && !firstFlag) {
                pkList = pkList + '-' + columnSplit[0];
              }
            });
            if (pkList === '') {
              save_disable.push(true);
            } else {
              bulkPrimaryKey.push(pkList);
              save_disable.push(false);
            }
            // bulkPrimaryKey.push(element.primaryKey);
          }
        }
      });
      for (let i = 0; i < save_disable.length; i++) {
        console.log(save_disable);
        if (save_disable[i] === true) {
          this.disable = true;
        }
      }
      if (this.disable) {
        const smsg = 'snowpoleApp.migrationProcess.primaryValidation';
        this.jhiAlertService.error(smsg);
      } else {
        this.migrationProcess.selectedColumns = JSON.stringify(this.selectedColumns);
        this.migrationProcess.tablesToMigrate = JSON.stringify(this.selectedTables);
        this.migrationProcess.cdc = cdc ? JSON.stringify(cdc) : null;
        this.migrationProcess.bulk = bulk ? JSON.stringify(bulk) : null;
        this.migrationProcess.cdcPk = cdcPrimaryKey ? JSON.stringify(cdcPrimaryKey) : null;
        this.migrationProcess.bulkPk = bulkPrimaryKey ? JSON.stringify(bulkPrimaryKey) : null;
        this.migrationProcess.cdcCols = cdcColumns ? JSON.stringify(cdcColumns) : null;
        this.subscribeToSaveResponse(this.migrationProcessService.update(this.migrationProcess));
      }
    } else {
      const smsg = 'snowpoleApp.migrationProcess.atleastOneTable';
      this.jhiAlertService.error(smsg);
    }
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
