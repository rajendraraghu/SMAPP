import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from './migration-process.service';
import { MigrationProcessComponent } from './migration-process.component';
import { MigrationProcessDetailComponent } from './migration-process-detail.component';
import { MigrationProcessUpdateComponent } from './migration-process-update.component';
import { MigrationProcessDeletePopupComponent } from './migration-process-delete-dialog.component';
import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessStatusComponent } from './migration-process-status.component';
import { MigrationProcessJobStatusComponent } from './migration-process-job-status.component';

@Injectable({ providedIn: 'root' })
export class MigrationProcessResolve implements Resolve<IMigrationProcess> {
  constructor(private service: MigrationProcessService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMigrationProcess> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MigrationProcess>) => response.ok),
        map((migrationProcess: HttpResponse<MigrationProcess>) => migrationProcess.body)
      );
    }
    return of(new MigrationProcess());
  }
}

export const migrationProcessRoute: Routes = [
  {
    path: '',
    component: MigrationProcessComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.migrationProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MigrationProcessDetailComponent,
    resolve: {
      migrationProcess: MigrationProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.migrationProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MigrationProcessUpdateComponent,
    resolve: {
      migrationProcess: MigrationProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.migrationProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history',
    component: MigrationProcessStatusComponent,
    resolve: {
      migrationProcess: MigrationProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.migrationProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/history/:jId/view',
    component: MigrationProcessJobStatusComponent,
    resolve: {
      snowDDL: MigrationProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowDDL.jobStatus.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MigrationProcessUpdateComponent,
    resolve: {
      migrationProcess: MigrationProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.migrationProcess.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const migrationProcessPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MigrationProcessDeletePopupComponent,
    resolve: {
      migrationProcess: MigrationProcessResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.migrationProcess.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
