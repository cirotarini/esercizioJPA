import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { OrdineComponent } from 'app/entities/ordine/ordine.component';
import { OrdineService } from 'app/entities/ordine/ordine.service';
import { Ordine } from 'app/shared/model/ordine.model';

describe('Component Tests', () => {
  describe('Ordine Management Component', () => {
    let comp: OrdineComponent;
    let fixture: ComponentFixture<OrdineComponent>;
    let service: OrdineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [OrdineComponent],
        providers: []
      })
        .overrideTemplate(OrdineComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdineComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdineService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ordine(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ordines && comp.ordines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
