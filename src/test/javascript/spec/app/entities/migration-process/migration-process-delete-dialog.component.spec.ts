/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SnowpoleTestModule } from '../../../test.module';
import { MigrationProcessDeleteDialogComponent } from 'app/entities/migration-process/migration-process-delete-dialog.component';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';

describe('Component Tests', () => {
  describe('MigrationProcess Management Delete Component', () => {
    let comp: MigrationProcessDeleteDialogComponent;
    let fixture: ComponentFixture<MigrationProcessDeleteDialogComponent>;
    let service: MigrationProcessService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [MigrationProcessDeleteDialogComponent]
      })
        .overrideTemplate(MigrationProcessDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MigrationProcessDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MigrationProcessService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
