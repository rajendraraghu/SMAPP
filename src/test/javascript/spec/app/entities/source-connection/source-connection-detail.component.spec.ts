/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SnowpoleTestModule } from '../../../test.module';
import { SourceConnectionDetailComponent } from 'app/entities/source-connection/source-connection-detail.component';
import { SourceConnection } from 'app/shared/model/source-connection.model';

describe('Component Tests', () => {
  describe('SourceConnection Management Detail Component', () => {
    let comp: SourceConnectionDetailComponent;
    let fixture: ComponentFixture<SourceConnectionDetailComponent>;
    const route = ({ data: of({ sourceConnection: new SourceConnection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [SourceConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SourceConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SourceConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sourceConnection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
