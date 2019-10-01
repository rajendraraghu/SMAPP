import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { AccountService, Role, RoleService } from 'app/core';
import { RoleMgmtComponent } from './role-management.component';
// import { RoleMgmtDetailComponent } from './user-management-detail.component';
import { RoleMgmtUpdateComponent } from './role-management-update.component';

@Injectable({ providedIn: 'root' })
export class RoleResolve implements CanActivate {
  constructor(private accountService: AccountService) {}

  canActivate() {
    return this.accountService.identity().then(account => this.accountService.hasAnyAuthority(['ROLE_ADMIN']));
  }
}

@Injectable({ providedIn: 'root' })
export class RoleMgmtResolve implements Resolve<any> {
  constructor(private service: RoleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const id = route.params['login'] ? route.params['login'] : null;
    if (id) {
      return this.service.find(id);
    }
    return new Role();
  }
}

export const roleMgmtRoute: Routes = [
  {
    path: 'role-management',
    component: RoleMgmtComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'roleManagement.home.title',
      defaultSort: 'id,asc'
    }
  },
  //   {
  //     path: 'role-management/:login/view',
  //     component: UserMgmtDetailComponent,
  //     resolve: {
  //       user: UserMgmtResolve
  //     },
  //     data: {
  //       pageTitle: 'userManagement.home.title'
  //     }
  //   },
  {
    path: 'role-management/new',
    component: RoleMgmtUpdateComponent,
    resolve: {
      user: RoleMgmtResolve
    }
  },
  {
    path: 'role-management/:login/edit',
    component: RoleMgmtUpdateComponent,
    resolve: {
      user: RoleMgmtResolve
    }
  }
];
