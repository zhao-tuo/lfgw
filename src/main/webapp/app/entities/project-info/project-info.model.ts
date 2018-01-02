import { BaseEntity } from './../../shared';

export class ProjectInfo implements BaseEntity {
    constructor(
        public id?: number,
        public projectId?: number,
        public projectName?: string,
    ) {
    }
}
