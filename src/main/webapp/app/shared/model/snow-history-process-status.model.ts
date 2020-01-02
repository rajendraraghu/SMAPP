import { Moment } from 'moment';

export interface ISnowHistoryProcessStatus {
  batchId?: number;
  processId?: number;
  name?: string;
  runBy?: string;
  startTime?: Moment;
  endTime?: Moment;
  totalTables?: number;
  successTables?: number;
  errorTables?: number;
  status?: string;
}

export class SnowHistoryProcessStatus implements ISnowHistoryProcessStatus {
  constructor(
    public batchId?: number,
    public processId?: number,
    public name?: string,
    public runBy?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public totalTables?: number,
    public successTables?: number,
    public errorTables?: number,
    public status?: string
  ) {}
}
