/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SnowpoleTestModule } from '../../../test.module';
import { SourceConnectionDeleteDialogComponent } from 'app/entities/source-connection/source-connection-delete-dialog.component';
import { SourceConnectionService } from 'app/entities/source-connection/source-connection.service';

describe('Component Tests', () => {
  describe('SourceConnection Management Delete Component', () => {
    let comp: SourceConnectionDeleteDialogComponent;
    let fixture: ComponentFixture<SourceConnectionDeleteDialogComponent>;
    let service: SourceConnectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [SourceConnectionDeleteDialogComponent]
      })
        .overrideTemplate(SourceConnectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SourceConnectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SourceConnectionService);
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
