import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SnowHistory } from 'app/shared/model/snow-history.model';
import { SnowHistoryService } from './snow-history.service';
import { SnowHistoryComponent } from './snow-history.component';
import { SnowHistoryDetailComponent } from './snow-history-detail.component';
import { SnowHistoryUpdateComponent } from './snow-history-update.component';
import { SnowHistoryDeletePopupComponent } from './snow-history-delete-dialog.component';
import { ISnowHistory } from 'app/shared/model/snow-history.model';
import { SnowHistoryProcessStatusComponent } from './snow-history-process-status.component';
import { SnowHistoryJobStatusComponent } from './snow-history-job-status.component';

@Injectable({ providedIn: 'root' })
export class SnowHistoryResolve implements Resolve<ISnowHistory> {
  constructor(private service: SnowHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISnowHistory> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SnowHistory>) => response.ok),
        map((snowHistory: HttpResponse<SnowHistory>) => snowHistory.body)
      );
    }
    return of(new SnowHistory());
  }
}

export const snowHistoryRoute: Routes = [
  {
    path: '',
    component: SnowHistoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.snowHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SnowHistoryDetailComponent,
    resolve: {
      snowHistory: SnowHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SnowHistoryUpdateComponent,
    resolve: {
      snowHistory: SnowHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history',
    component: SnowHistoryProcessStatusComponent,
    resolve: {
      snowHistory: SnowHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history/:jId/view',
    component: SnowHistoryJobStatusComponent,
    resolve: {
      snowDDL: SnowHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowDDL.jobStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SnowHistoryUpdateComponent,
    resolve: {
      snowHistory: SnowHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const snowHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SnowHistoryDeletePopupComponent,
    resolve: {
      snowHistory: SnowHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
