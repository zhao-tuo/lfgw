/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt1DeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1-delete-dialog.component';
import { SaleAmt1Service } from '../../../../../../main/webapp/app/entities/sale-amt-1/sale-amt-1.service';

describe('Component Tests', () => {

    describe('SaleAmt1 Management Delete Component', () => {
        let comp: SaleAmt1DeleteDialogComponent;
        let fixture: ComponentFixture<SaleAmt1DeleteDialogComponent>;
        let service: SaleAmt1Service;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt1DeleteDialogComponent],
                providers: [
                    SaleAmt1Service
                ]
            })
            .overrideTemplate(SaleAmt1DeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt1DeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt1Service);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
