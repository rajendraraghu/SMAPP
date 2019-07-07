import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISnowflakeConnection } from 'app/shared/model/snowflake-connection.model';
import { SnowflakeConnectionService } from './snowflake-connection.service';

@Component({
  selector: 'jhi-snowflake-connection-delete-dialog',
  templateUrl: './snowflake-connection-delete-dialog.component.html'
})
export class SnowflakeConnectionDeleteDialogComponent {
  snowflakeConnection: ISnowflakeConnection;

  constructor(
    protected snowflakeConnectionService: SnowflakeConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.snowflakeConnectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'snowflakeConnectionListModification',
        content: 'Deleted an snowflakeConnection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-snowflake-connection-delete-popup',
  template: ''
})
export class SnowflakeConnectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowflakeConnection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SnowflakeConnectionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.snowflakeConnection = snowflakeConnection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/snowflake-connection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/snowflake-connection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
