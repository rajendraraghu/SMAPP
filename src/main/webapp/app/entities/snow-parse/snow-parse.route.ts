import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SnowParse } from 'app/shared/model/snow-parse.model';
import { SnowParseService } from './snow-parse.service';
import { SnowParseComponent } from './snow-parse.component';
import { SnowParseUpdateComponent } from './snow-parse-update.component';
import { SnowParseDeletePopupComponent } from './snow-parse-delete-dialog.component';
import { ISnowParse } from 'app/shared/model/snow-parse.model';
import { SnowParseProcessStatusComponent } from 'app/entities/snow-parse/snow-parse-process-status.component';
import { SnowParseJobStatusComponent } from './snow-parse-job-status.component';
import { ISnowParseProcessStatus, SnowParseProcessStatus } from 'app/shared/model/snow-parse-process-status.model';

@Injectable({ providedIn: 'root' })
export class SnowParseResolve implements Resolve<ISnowParse> {
  constructor(private service: SnowParseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISnowParse> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SnowParse>) => response.ok),
        map((snowParse: HttpResponse<SnowParse>) => snowParse.body)
      );
    }
    return of(new SnowParse());
  }
}

export const snowParseRoute: Routes = [
  {
    path: '',
    component: SnowParseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.snowParse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SnowParseUpdateComponent,
    resolve: {
      snowParse: SnowParseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowParse.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history',
    component: SnowParseProcessStatusComponent,
    resolve: {
      snowParse: SnowParseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowParse.processStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history/:jId/view',
    component: SnowParseJobStatusComponent,
    resolve: {
      snowDDL: SnowParseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowParse.jobStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SnowParseUpdateComponent,
    resolve: {
      snowParse: SnowParseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowParse.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const snowParsePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SnowParseDeletePopupComponent,
    resolve: {
      snowParse: SnowParseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.snowParse.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
