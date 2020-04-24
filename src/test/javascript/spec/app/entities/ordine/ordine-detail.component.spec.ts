import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { OrdineDetailComponent } from 'app/entities/ordine/ordine-detail.component';
import { Ordine } from 'app/shared/model/ordine.model';

describe('Component Tests', () => {
  describe('Ordine Management Detail Component', () => {
    let comp: OrdineDetailComponent;
    let fixture: ComponentFixture<OrdineDetailComponent>;
    const route = ({ data: of({ ordine: new Ordine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [OrdineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OrdineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrdineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ordine on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ordine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
