/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SnowpoleTestModule } from '../../../test.module';
import { SourceConnectionUpdateComponent } from 'app/entities/source-connection/source-connection-update.component';
import { SourceConnectionService } from 'app/entities/source-connection/source-connection.service';
import { SourceConnection } from 'app/shared/model/source-connection.model';

describe('Component Tests', () => {
  describe('SourceConnection Management Update Component', () => {
    let comp: SourceConnectionUpdateComponent;
    let fixture: ComponentFixture<SourceConnectionUpdateComponent>;
    let service: SourceConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [SourceConnectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SourceConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SourceConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SourceConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SourceConnection(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SourceConnection();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
