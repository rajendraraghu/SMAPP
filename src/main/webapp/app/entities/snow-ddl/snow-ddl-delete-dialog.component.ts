import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISnowDDL } from 'app/shared/model/snow-ddl.model';
import { SnowDDLService } from './snow-ddl.service';

@Component({
  selector: 'jhi-snow-ddl-delete-dialog',
  templateUrl: './snow-ddl-delete-dialog.component.html'
})
export class SnowDDLDeleteDialogComponent {
  snowDDL: ISnowDDL;

  constructor(protected snowDDLService: SnowDDLService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.snowDDLService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'snowDDLListModification',
        content: 'Deleted a snowDDL'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-snow-ddl-delete-popup',
  template: ''
})
export class SnowDDLDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowDDL }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SnowDDLDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.snowDDL = snowDDL;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/snow-ddl', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/snow-ddl', { outlets: { popup: null } }]);
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
