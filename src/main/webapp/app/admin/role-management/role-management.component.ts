import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITEMS_PER_PAGE } from 'app/shared';
import { AccountService, RoleService, Role } from 'app/core';
import { UserMgmtDeleteDialogComponent } from './role-management-delete-dialog.component';

@Component({
  selector: 'jhi-role-mgmt',
  templateUrl: './role-management.component.html'
})
export class RoleMgmtComponent implements OnInit, OnDestroy {
  currentAccount: any;
  roles: Role[];
  error: any;
  success: any;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    private toleService: RoleService,
    private alertService: JhiAlertService,
    private accountService: AccountService,
    private parseLinks: JhiParseLinks,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data['pagingParams'].page;
      this.previousPage = data['pagingParams'].page;
      this.reverse = data['pagingParams'].ascending;
      this.predicate = data['pagingParams'].predicate;
    });
  }

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.currentAccount = account;
      this.loadAll();
      this.registerChangeInUsers();
    });
  }

  ngOnDestroy() {
    this.routeData.unsubscribe();
  }

  registerChangeInUsers() {
    this.eventManager.subscribe('userListModification', response => this.loadAll());
  }

  setActive(user, isActivated) {
    user.activated = isActivated;

    this.roleService.update(role).subscribe(response => {
      if (response.status === 200) {
        this.error = null;
        this.success = 'OK';
        this.loadAll();
      } else {
        this.success = null;
        this.error = 'ERROR';
      }
    });
  }

  loadAll() {
    this.roleService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<Role[]>) => this.onSuccess(res.body, res.headers), (res: HttpResponse<any>) => this.onError(res.body));
  }

  trackIdentity(index, item: Role) {
    return item.id;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/admin/role-management'], {
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  deleteUser(user: Role) {
    const modalRef = this.modalService.open(UserMgmtDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    modalRef.result.then(
      result => {
        // Left blank intentionally, nothing to do here
      },
      reason => {
        // Left blank intentionally, nothing to do here
      }
    );
  }

  private onSuccess(data, headers) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = headers.get('X-Total-Count');
    this.roles = data;
  }

  private onError(error) {
    this.alertService.error(error.error, error.message, null);
  }
}
