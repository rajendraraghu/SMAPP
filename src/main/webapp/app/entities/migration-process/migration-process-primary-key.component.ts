import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMigrationProcess } from 'app/shared/model/migration-process.model';
import { MigrationProcessService } from './migration-process.service';

@Component({
  selector: 'jhi-migration-process-select-primarykey',
  templateUrl: './migration-process-primary-key.component.html'
})
export class MigrationProcessPrimaryKeyComponent implements OnInit {
  migrationProcess: IMigrationProcess;

  constructor(
    protected migrationProcessService: MigrationProcessService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit() {
    console.log('Primary KEy modal');
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }
}

@Component({
  selector: 'jhi-migration-process-select-primarykey-popup',
  template: ''
})
export class MigrationProcessPrimaryKeyPopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    console.log('Primary KEy PopUp');
    this.activatedRoute.data.subscribe(({ migrationProcess }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MigrationProcessPrimaryKeyComponent as Component, { size: 'lg', backdrop: 'static' });
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
