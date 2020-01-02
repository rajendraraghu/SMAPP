import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISnowParse } from 'app/shared/model/snow-parse.model';
import { SnowParseService } from './snow-parse.service';

@Component({
  selector: 'jhi-snow-parse-delete-dialog',
  templateUrl: './snow-parse-delete-dialog.component.html'
})
export class SnowParseDeleteDialogComponent {
  snowParse: ISnowParse;

  constructor(protected snowParseService: SnowParseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.snowParseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'snowParseListModification',
        content: 'Deleted a snowParse'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-snow-parse-delete-popup',
  template: ''
})
export class SnowParseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ snowParse }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SnowParseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.snowParse = snowParse;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/snow-parse', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/snow-parse', { outlets: { popup: null } }]);
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
