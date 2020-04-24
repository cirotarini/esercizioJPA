import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { ProdottoDetailComponent } from 'app/entities/prodotto/prodotto-detail.component';
import { Prodotto } from 'app/shared/model/prodotto.model';

describe('Component Tests', () => {
  describe('Prodotto Management Detail Component', () => {
    let comp: ProdottoDetailComponent;
    let fixture: ComponentFixture<ProdottoDetailComponent>;
    const route = ({ data: of({ prodotto: new Prodotto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [ProdottoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProdottoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProdottoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prodotto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prodotto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
