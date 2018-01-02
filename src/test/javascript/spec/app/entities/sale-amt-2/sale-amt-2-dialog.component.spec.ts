/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt2DialogComponent } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2-dialog.component';
import { SaleAmt2Service } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.service';
import { SaleAmt2 } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.model';

describe('Component Tests', () => {

    describe('SaleAmt2 Management Dialog Component', () => {
        let comp: SaleAmt2DialogComponent;
        let fixture: ComponentFixture<SaleAmt2DialogComponent>;
        let service: SaleAmt2Service;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt2DialogComponent],
                providers: [
                    SaleAmt2Service
                ]
            })
            .overrideTemplate(SaleAmt2DialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt2DialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt2Service);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SaleAmt2(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.saleAmt2 = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'saleAmt2ListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SaleAmt2();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.saleAmt2 = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'saleAmt2ListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
