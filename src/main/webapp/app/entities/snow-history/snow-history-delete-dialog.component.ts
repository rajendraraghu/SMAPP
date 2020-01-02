import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISnowHistory } from 'app/shared/model/snow-history.model';
import { SnowHistoryService } from './snow-history.service';

@Component({
  selector: 'jhi-snow-history-delete-dialog',
  templateUrl: './snow-history-delete-dialog.component.html'
})
export class SnowHistoryDeleteDialogComponent {
  snowHistory: ISnowHistory;

  constructor(
    protected snowHistoryService: SnowHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.snowHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'snowHistoryListModification',
        content: 'Deleted an snowHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-snow-history-delete-popup',
  template: ''
})
export class SnowHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SnowHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.snowHistory = snowHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/snow-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/snow-history', { outlets: { popup: null } }]);
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
