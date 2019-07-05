import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISourceConnection } from 'app/shared/model/source-connection.model';
import { SourceConnectionService } from './source-connection.service';

@Component({
  selector: 'jhi-source-connection-delete-dialog',
  templateUrl: './source-connection-delete-dialog.component.html'
})
export class SourceConnectionDeleteDialogComponent {
  sourceConnection: ISourceConnection;

  constructor(
    protected sourceConnectionService: SourceConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sourceConnectionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sourceConnectionListModification',
        content: 'Deleted an sourceConnection'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-source-connection-delete-popup',
  template: ''
})
export class SourceConnectionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sourceConnection }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SourceConnectionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sourceConnection = sourceConnection;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/source-connection', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/source-connection', { outlets: { popup: null } }]);
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
