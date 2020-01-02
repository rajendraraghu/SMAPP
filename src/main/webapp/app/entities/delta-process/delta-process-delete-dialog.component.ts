import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeltaProcess } from 'app/shared/model/delta-process.model';
import { DeltaProcessService } from './delta-process.service';

@Component({
  selector: 'jhi-delta-process-delete-dialog',
  templateUrl: './delta-process-delete-dialog.component.html'
})
export class DeltaProcessDeleteDialogComponent {
  deltaProcess: IDeltaProcess;

  constructor(
    protected deltaProcessService: DeltaProcessService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deltaProcessService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'deltaProcessListModification',
        content: 'Deleted an deltaProcess'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-delta-process-delete-popup',
  template: ''
})
export class DeltaProcessDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deltaProcess }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeltaProcessDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deltaProcess = deltaProcess;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/delta-process', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/delta-process', { outlets: { popup: null } }]);
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
