/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt2DeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2-delete-dialog.component';
import { SaleAmt2Service } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.service';

describe('Component Tests', () => {

    describe('SaleAmt2 Management Delete Component', () => {
        let comp: SaleAmt2DeleteDialogComponent;
        let fixture: ComponentFixture<SaleAmt2DeleteDialogComponent>;
        let service: SaleAmt2Service;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt2DeleteDialogComponent],
                providers: [
                    SaleAmt2Service
                ]
            })
            .overrideTemplate(SaleAmt2DeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt2DeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt2Service);
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
