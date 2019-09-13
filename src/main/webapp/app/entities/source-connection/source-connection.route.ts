import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from './source-connection.service';
import { SourceConnectionComponent } from './source-connection.component';
import { SourceConnectionDetailComponent } from './source-connection-detail.component';
import { SourceConnectionUpdateComponent } from './source-connection-update.component';
import { SourceConnectionDeletePopupComponent } from './source-connection-delete-dialog.component';
import { ISourceConnection } from 'app/shared/model/source-connection.model';

@Injectable({ providedIn: 'root' })
export class SourceConnectionResolve implements Resolve<ISourceConnection> {
  constructor(private service: SourceConnectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISourceConnection> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SourceConnection>) => response.ok),
        map((sourceConnection: HttpResponse<SourceConnection>) => sourceConnection.body)
      );
    }
    return of(new SourceConnection());
  }
}

export const sourceConnectionRoute: Routes = [
  {
    path: '',
    component: SourceConnectionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.sourceConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SourceConnectionDetailComponent,
    resolve: {
      sourceConnection: SourceConnectionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_DEVELOPER'],
      pageTitle: 'snowpoleApp.sourceConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SourceConnectionUpdateComponent,
    resolve: {
      sourceConnection: SourceConnectionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'snowpoleApp.sourceConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SourceConnectionUpdateComponent,
    resolve: {
      sourceConnection: SourceConnectionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'snowpoleApp.sourceConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sourceConnectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SourceConnectionDeletePopupComponent,
    resolve: {
      sourceConnection: SourceConnectionResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'snowpoleApp.sourceConnection.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
