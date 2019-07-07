import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';

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

  constructor(protected activatedRoute: ActivatedRoute, protected migrationProcessService: MigrationProcessService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcess = migrationProcess;
      this.getTableList();
    });
  }

  previousState() {
    window.history.back();
  }

  getTableList() {
    this.migrationProcessService.getTableList(this.migrationProcess).subscribe(response => {
      this.tables = this.tablesCopy = response.body;
    });
  }

  onSelectionChange(item) {
    const index = this.selectedTables.indexOf(item);
    if (index === -1) {
      // val not found, pushing onto array
      this.selectedTables.push(item);
    } else {
      // val is found, removing from array
      this.selectedTables.splice(index, 1);
    }
  }

  isChecked(item) {
    const index = this.selectedTables.indexOf(item);
    if (index === -1) {
      return false;
    } else {
      return true;
    }
  }

  searchForTables(val) {
    if (val) {
      this.tables = this.tablesCopy.filter(option => {
        return option.toLowerCase().includes(val.toLowerCase());
      });
      return this.tables;
    } else {
      this.tables = this.tablesCopy;
    }
  }

  testAndMigrate() {
    // this.connectionService.testAndMigrate(this.connection, this.selectedTables).subscribe(response => {
    //   this.tables = this.tablesCopy = response.body;
    // });
  }
}
