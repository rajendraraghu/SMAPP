/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SnowpoleTestModule } from '../../../test.module';
import { SnowflakeConnectionUpdateComponent } from 'app/entities/snowflake-connection/snowflake-connection-update.component';
import { SnowflakeConnectionService } from 'app/entities/snowflake-connection/snowflake-connection.service';
import { SnowflakeConnection } from 'app/shared/model/snowflake-connection.model';

describe('Component Tests', () => {
  describe('SnowflakeConnection Management Update Component', () => {
    let comp: SnowflakeConnectionUpdateComponent;
    let fixture: ComponentFixture<SnowflakeConnectionUpdateComponent>;
    let service: SnowflakeConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [SnowflakeConnectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SnowflakeConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SnowflakeConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SnowflakeConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SnowflakeConnection(123);
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
        const entity = new SnowflakeConnection();
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
