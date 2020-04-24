import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { ProdottoComponent } from 'app/entities/prodotto/prodotto.component';
import { ProdottoService } from 'app/entities/prodotto/prodotto.service';
import { Prodotto } from 'app/shared/model/prodotto.model';

describe('Component Tests', () => {
  describe('Prodotto Management Component', () => {
    let comp: ProdottoComponent;
    let fixture: ComponentFixture<ProdottoComponent>;
    let service: ProdottoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [ProdottoComponent],
        providers: []
      })
        .overrideTemplate(ProdottoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProdottoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProdottoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Prodotto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prodottos && comp.prodottos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
