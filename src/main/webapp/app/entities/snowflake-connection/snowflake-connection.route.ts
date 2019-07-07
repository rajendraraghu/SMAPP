import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from './snowflake-connection.service';
import { SnowflakeConnectionComponent } from './snowflake-connection.component';
import { SnowflakeConnectionDetailComponent } from './snowflake-connection-detail.component';
import { SnowflakeConnectionUpdateComponent } from './snowflake-connection-update.component';
import { SnowflakeConnectionDeletePopupComponent } from './snowflake-connection-delete-dialog.component';
import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';

@Injectable({ providedIn: 'root' })
export class SnowflakeConnectionResolve implements Resolve<ISnowflakeConnection> {
  constructor(private service: SnowflakeConnectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISnowflakeConnection> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SnowflakeConnection>) => response.ok),
        map((snowflakeConnection: HttpResponse<SnowflakeConnection>) => snowflakeConnection.body)
      );
    }
    return of(new SnowflakeConnection());
  }
}

export const snowflakeConnectionRoute: Routes = [
  {
    path: '',
    component: SnowflakeConnectionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'snowpoleApp.snowflakeConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SnowflakeConnectionDetailComponent,
    resolve: {
      snowflakeConnection: SnowflakeConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowflakeConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SnowflakeConnectionUpdateComponent,
    resolve: {
      snowflakeConnection: SnowflakeConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowflakeConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SnowflakeConnectionUpdateComponent,
    resolve: {
      snowflakeConnection: SnowflakeConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowflakeConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const snowflakeConnectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SnowflakeConnectionDeletePopupComponent,
    resolve: {
      snowflakeConnection: SnowflakeConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'snowpoleApp.snowflakeConnection.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
