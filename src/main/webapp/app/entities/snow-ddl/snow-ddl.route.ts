import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SnowDDL } from 'app/shared/model/snow-ddl.model';
import { SnowDDLService } from './snow-ddl.service';
import { SnowDDLComponent } from './snow-ddl.component';
import { SnowDDLUpdateComponent } from './snow-ddl-update.component';
import { SnowDDLDeletePopupComponent } from './snow-ddl-delete-dialog.component';
import { ISnowDDL } from 'app/shared/model/snow-ddl.model';
import { SnowDDLProcessStatusComponent } from 'app/entities/snow-ddl/snow-ddl-process-status.component';
import { SnowDDLJobStatusComponent } from './snow-ddl-job-status.component';
import { ISnowDDLProcessStatus, SnowDDLProcessStatus } from 'app/shared/model/snow-ddl-process-status.model';

@Injectable({ providedIn: 'root' })
export class SnowDDLResolve implements Resolve<ISnowDDL> {
  constructor(private service: SnowDDLService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISnowDDL> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SnowDDL>) => response.ok),
        map((snowDDL: HttpResponse<SnowDDL>) => snowDDL.body)
      );
    }
    return of(new SnowDDL());
  }
}

export const snowDDLRoute: Routes = [
  {
    path: '',
    component: SnowDDLComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.snowDDL.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SnowDDLUpdateComponent,
    resolve: {
      snowDDL: SnowDDLResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history',
    component: SnowDDLProcessStatusComponent,
    resolve: {
      snowDDL: SnowDDLResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.processStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history/:jId/view',
    component: SnowDDLJobStatusComponent,
    resolve: {
      snowDDL: SnowDDLResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.jobStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SnowDDLUpdateComponent,
    resolve: {
      snowDDL: SnowDDLResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const snowDDLPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SnowDDLDeletePopupComponent,
    resolve: {
      snowDDL: SnowDDLResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
