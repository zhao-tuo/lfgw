/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { LfgwTestModule } from '../../../test.module';
import { ProjectInfoDetailComponent } from '../../../../../../main/webapp/app/entities/project-info/project-info-detail.component';
import { ProjectInfoService } from '../../../../../../main/webapp/app/entities/project-info/project-info.service';
import { ProjectInfo } from '../../../../../../main/webapp/app/entities/project-info/project-info.model';

describe('Component Tests', () => {

    describe('ProjectInfo Management Detail Component', () => {
        let comp: ProjectInfoDetailComponent;
        let fixture: ComponentFixture<ProjectInfoDetailComponent>;
        let service: ProjectInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LfgwTestModule],
                declarations: [ProjectInfoDetailComponent],
                providers: [
                    ProjectInfoService
                ]
            })
            .overrideTemplate(ProjectInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProjectInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProjectInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ProjectInfo(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.projectInfo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
