/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SnowpoleTestModule } from '../../../test.module';
import { MigrationProcessDetailComponent } from 'app/entities/migration-process/migration-process-detail.component';
import { MigrationProcess } from 'app/shared/model/migration-process.model';

describe('Component Tests', () => {
  describe('MigrationProcess Management Detail Component', () => {
    let comp: MigrationProcessDetailComponent;
    let fixture: ComponentFixture<MigrationProcessDetailComponent>;
    const route = ({ data: of({ migrationProcess: new MigrationProcess(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [MigrationProcessDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MigrationProcessDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MigrationProcessDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.migrationProcess).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
