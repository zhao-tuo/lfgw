/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { LfgwTestModule } from '../../../test.module';
import { ProjectTransactionRecordDetailComponent } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record-detail.component';
import { ProjectTransactionRecordService } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.service';
import { ProjectTransactionRecord } from '../../../../../../main/webapp/app/entities/project-transaction-record/project-transaction-record.model';

describe('Component Tests', () => {

    describe('ProjectTransactionRecord Management Detail Component', () => {
        let comp: ProjectTransactionRecordDetailComponent;
        let fixture: ComponentFixture<ProjectTransactionRecordDetailComponent>;
        let service: ProjectTransactionRecordService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectTransactionRecordDetailComponent],
                providers: [
                    ProjectTransactionRecordService
                ]
            })
            .overrideTemplate(ProjectTransactionRecordDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectTransactionRecordDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectTransactionRecordService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ProjectTransactionRecord(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.projectTransactionRecord).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
