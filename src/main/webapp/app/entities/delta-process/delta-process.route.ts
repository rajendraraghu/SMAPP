import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessService } from './delta-process.service';
import { DeltaProcessComponent } from './delta-process.component';
import { DeltaProcessDetailComponent } from './delta-process-detail.component';
import { DeltaProcessUpdateComponent } from './delta-process-update.component';
import { DeltaProcessDeletePopupComponent } from './delta-process-delete-dialog.component';
import { IDeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessStatusComponent } from './delta-process-status.component';
import { DeltaProcessJobStatusComponent } from './delta-process-job-status.component';

@Injectable({ providedIn: 'root' })
export class DeltaProcessResolve implements Resolve<IDeltaProcess> {
  constructor(private service: DeltaProcessService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDeltaProcess> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DeltaProcess>) => response.ok),
        map((deltaProcess: HttpResponse<DeltaProcess>) => deltaProcess.body)
      );
    }
    return of(new DeltaProcess());
  }
}

export const deltaProcessRoute: Routes = [
  {
    path: '',
    component: DeltaProcessComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.deltaProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeltaProcessDetailComponent,
    resolve: {
      deltaProcess: DeltaProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.deltaProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeltaProcessUpdateComponent,
    resolve: {
      deltaProcess: DeltaProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.deltaProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history',
    component: DeltaProcessStatusComponent,
    resolve: {
      deltaProcess: DeltaProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.deltaProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history/:jId/view',
    component: DeltaProcessJobStatusComponent,
    resolve: {
      snowDDL: DeltaProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.jobStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeltaProcessUpdateComponent,
    resolve: {
      deltaProcess: DeltaProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.deltaProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deltaProcessPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeltaProcessDeletePopupComponent,
    resolve: {
      deltaProcess: DeltaProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.deltaProcess.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
