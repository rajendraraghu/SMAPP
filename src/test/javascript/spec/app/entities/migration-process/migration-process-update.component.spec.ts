/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SnowpoleTestModule } from '../../../test.module';
import { MigrationProcessUpdateComponent } from 'app/entities/migration-process/migration-process-update.component';
import { MigrationProcessService } from 'app/entities/migration-process/migration-process.service';
import { MigrationProcess } from 'app/shared/model/migration-process.model';

describe('Component Tests', () => {
  describe('MigrationProcess Management Update Component', () => {
    let comp: MigrationProcessUpdateComponent;
    let fixture: ComponentFixture<MigrationProcessUpdateComponent>;
    let service: MigrationProcessService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [MigrationProcessUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MigrationProcessUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MigrationProcessUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MigrationProcessService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MigrationProcess(123);
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
        const entity = new MigrationProcess();
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
