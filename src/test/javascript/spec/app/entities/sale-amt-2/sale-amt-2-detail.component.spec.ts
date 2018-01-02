/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt2DetailComponent } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2-detail.component';
import { SaleAmt2Service } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.service';
import { SaleAmt2 } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.model';

describe('Component Tests', () => {

    describe('SaleAmt2 Management Detail Component', () => {
        let comp: SaleAmt2DetailComponent;
        let fixture: ComponentFixture<SaleAmt2DetailComponent>;
        let service: SaleAmt2Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt2DetailComponent],
                providers: [
                    SaleAmt2Service
                ]
            })
            .overrideTemplate(SaleAmt2DetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt2DetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt2Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new SaleAmt2(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.saleAmt2).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
