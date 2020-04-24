import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EsercizioJpa032020TestModule } from '../../../test.module';
import { OrdineUpdateComponent } from 'app/entities/ordine/ordine-update.component';
import { OrdineService } from 'app/entities/ordine/ordine.service';
import { Ordine } from 'app/shared/model/ordine.model';

describe('Component Tests', () => {
  describe('Ordine Management Update Component', () => {
    let comp: OrdineUpdateComponent;
    let fixture: ComponentFixture<OrdineUpdateComponent>;
    let service: OrdineService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EsercizioJpa032020TestModule],
        declarations: [OrdineUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OrdineUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrdineUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrdineService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ordine(123);
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
        const entity = new Ordine();
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
