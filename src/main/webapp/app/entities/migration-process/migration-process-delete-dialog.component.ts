import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from './migration-process.service';

@Component({
  selector: 'jhi-migration-process-delete-dialog',
  templateUrl: './migration-process-delete-dialog.component.html'
})
export class MigrationProcessDeleteDialogComponent {
  migrationProcess: IMigrationProcess;

  constructor(
    protected migrationProcessService: MigrationProcessService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.migrationProcessService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'migrationProcessListModification',
        content: 'Deleted an migrationProcess'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-migration-process-delete-popup',
  template: ''
})
export class MigrationProcessDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MigrationProcessDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.migrationProcess = migrationProcess;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/migration-process', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/migration-process', { outlets: { popup: null } }]);
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
