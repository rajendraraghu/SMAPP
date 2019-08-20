import { Moment } from 'moment';

export interface IDeltaProcessJobStatus {
  jobId?: number;
  tableLoadId?: number;
  tableName?: string;
  tableLoadStartTime?: Moment;
  tableLoadEndTime?: Moment;
  tableLoadStatus?: string;
  insertCount?: number;
  updateCount?: number;
  deleteCount?: number;
  runType?: string;
  processId?: number;
  processName?: string;
  sourceName?: string;
  destName?: string;
}

export class DeltaProcessJobStatus implements IDeltaProcessJobStatus {
  constructor(
    public jobId?: number,
    public tableLoadId?: number,
    public tableName?: string,
    public tableLoadStartTime?: Moment,
    public tableLoadEndTime?: Moment,
    public tableLoadStatus?: string,
    public insertCount?: number,
    public updateCount?: number,
    public deleteCount?: number,
    public runType?: string,
    public processId?: number,
    public processName?: string,
    public sourceName?: string,
    public destName?: string
  ) {}
}
