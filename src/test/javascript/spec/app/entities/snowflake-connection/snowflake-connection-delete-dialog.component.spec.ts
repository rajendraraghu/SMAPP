/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SnowpoleTestModule } from '../../../test.module';
import { SnowflakeConnectionDeleteDialogComponent } from 'app/entities/snowflake-connection/snowflake-connection-delete-dialog.component';
import { SnowflakeConnectionService } from 'app/entities/snowflake-connection/snowflake-connection.service';

describe('Component Tests', () => {
  describe('SnowflakeConnection Management Delete Component', () => {
    let comp: SnowflakeConnectionDeleteDialogComponent;
    let fixture: ComponentFixture<SnowflakeConnectionDeleteDialogComponent>;
    let service: SnowflakeConnectionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [SnowflakeConnectionDeleteDialogComponent]
      })
        .overrideTemplate(SnowflakeConnectionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SnowflakeConnectionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SnowflakeConnectionService);
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
