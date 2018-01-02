/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { LfgwTestModule } from '../../../test.module';
import { SaleAmt2Component } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.component';
import { SaleAmt2Service } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.service';
import { SaleAmt2 } from '../../../../../../main/webapp/app/entities/sale-amt-2/sale-amt-2.model';

describe('Component Tests', () => {

    describe('SaleAmt2 Management Component', () => {
        let comp: SaleAmt2Component;
        let fixture: ComponentFixture<SaleAmt2Component>;
        let service: SaleAmt2Service;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [SaleAmt2Component],
                providers: [
                    SaleAmt2Service
                ]
            })
            .overrideTemplate(SaleAmt2Component, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SaleAmt2Component);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleAmt2Service);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new SaleAmt2(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.saleAmt2S[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
