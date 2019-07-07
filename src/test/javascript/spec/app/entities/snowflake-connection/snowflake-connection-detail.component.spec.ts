/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SnowpoleTestModule } from '../../../test.module';
import { SnowflakeConnectionDetailComponent } from 'app/entities/snowflake-connection/snowflake-connection-detail.component';
import { SnowflakeConnection } from 'app/shared/model/snowflake-connection.model';

describe('Component Tests', () => {
  describe('SnowflakeConnection Management Detail Component', () => {
    let comp: SnowflakeConnectionDetailComponent;
    let fixture: ComponentFixture<SnowflakeConnectionDetailComponent>;
    const route = ({ data: of({ snowflakeConnection: new SnowflakeConnection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SnowpoleTestModule],
        declarations: [SnowflakeConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SnowflakeConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SnowflakeConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.snowflakeConnection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
