import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { RigaOrdineDetailComponent } from 'app/entities/riga-ordine/riga-ordine-detail.component';
import { RigaOrdine } from 'app/shared/model/riga-ordine.model';

describe('Component Tests', () => {
  describe('RigaOrdine Management Detail Component', () => {
    let comp: RigaOrdineDetailComponent;
    let fixture: ComponentFixture<RigaOrdineDetailComponent>;
    const route = ({ data: of({ rigaOrdine: new RigaOrdine(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [RigaOrdineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RigaOrdineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RigaOrdineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rigaOrdine on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rigaOrdine).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
