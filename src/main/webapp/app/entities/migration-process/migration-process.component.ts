import { Component, OnInit, OnDestroy, SystemJsNgModuleLoader } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { MigrationProcessService } from './migration-process.service';

@Component({
  selector: 'jhi-migration-process',
  templateUrl: './migration-process.component.html'
})
export class MigrationProcessComponent implements OnInit, OnDestroy {
  currentAccount: any;
  migrationProcesses: IMigrationProcess[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  runDisable: boolean;
  id: any;

  constructor(
    protected migrationProcessService: MigrationProcessService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.migrationProcessService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IMigrationProcess[]>) => this.paginateMigrationProcesses(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/migration-process'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/migration-process',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      this.migrationProcesses = migrationProcess;
    });
    this.registerChangeInMigrationProcesses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMigrationProcess) {
    return item.id;
  }

  registerChangeInMigrationProcesses() {
    this.eventSubscriber = this.eventManager.subscribe('migrationProcessListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  sendTableList(migrationProcess) {
    // migrationProcess.isRunning = true;
    this.id = migrationProcess.id;
    if (migrationProcess.bulk === null && migrationProcess.cdc === null) {
      const smsg = 'snowpoleApp.migrationProcess.atleastOneTable';
      this.jhiAlertService.error(smsg);
    } else {
      migrationProcess.isRunning = true;
      migrationProcess.runBy = this.currentAccount.login;
      this.migrationProcessService.sendTableList(migrationProcess).subscribe(response => {
        migrationProcess.isRunning = !response.ok;
        if (response.ok) {
          const smsg = 'snowpoleApp.migrationProcess.migrationCompleted';
          this.jhiAlertService.success(smsg);
        }
      });
    }
    // migrationProcess.isRunning = false;
  }

  viewProcessStatus(processId) {
    this.router.navigate(['/migration-process', processId, 'history']);
  }

  protected paginateMigrationProcesses(data: IMigrationProcess[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.migrationProcesses = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
