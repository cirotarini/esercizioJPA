import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { RigaOrdineUpdateComponent } from 'app/entities/riga-ordine/riga-ordine-update.component';
import { RigaOrdineService } from 'app/entities/riga-ordine/riga-ordine.service';
import { RigaOrdine } from 'app/shared/model/riga-ordine.model';

describe('Component Tests', () => {
  describe('RigaOrdine Management Update Component', () => {
    let comp: RigaOrdineUpdateComponent;
    let fixture: ComponentFixture<RigaOrdineUpdateComponent>;
    let service: RigaOrdineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [RigaOrdineUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RigaOrdineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RigaOrdineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RigaOrdineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RigaOrdine(123);
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
        const entity = new RigaOrdine();
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
