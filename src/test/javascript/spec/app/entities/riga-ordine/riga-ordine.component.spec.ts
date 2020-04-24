import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { RigaOrdineComponent } from 'app/entities/riga-ordine/riga-ordine.component';
import { RigaOrdineService } from 'app/entities/riga-ordine/riga-ordine.service';
import { RigaOrdine } from 'app/shared/model/riga-ordine.model';

describe('Component Tests', () => {
  describe('RigaOrdine Management Component', () => {
    let comp: RigaOrdineComponent;
    let fixture: ComponentFixture<RigaOrdineComponent>;
    let service: RigaOrdineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [RigaOrdineComponent],
        providers: []
      })
        .overrideTemplate(RigaOrdineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RigaOrdineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RigaOrdineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RigaOrdine(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rigaOrdines && comp.rigaOrdines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
