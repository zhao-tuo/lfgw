/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt1DialogComponent } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1-dialog.component';
import { SaleAmt1Service } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.service';
import { SaleAmt1 } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.model';

describe('Component Tests', () => {

    describe('SaleAmt1 Management Dialog Component', () => {
        let comp: SaleAmt1DialogComponent;
        let fixture: ComponentFixture<SaleAmt1DialogComponent>;
        let service: SaleAmt1Service;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt1DialogComponent],
                providers: [
                    SaleAmt1Service
                ]
            })
            .overrideTemplate(SaleAmt1DialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt1DialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt1Service);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SaleAmt1(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.saleAmt1 = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'saleAmt1ListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SaleAmt1();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.saleAmt1 = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'saleAmt1ListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
