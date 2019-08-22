import { Moment } from 'moment';

export interface ISnowParseProcessStatus {
  batchId?: number;
  processId?: number;
  name?: string;
  runBy?: string;
  startTime?: Moment;
  endTime?: Moment;
  totalObjects?: number;
  successObjects?: number;
  errorObjects?: number;
  status?: string;
}

export class SnowParseProcessStatus implements ISnowParseProcessStatus {
  constructor(
    public batchId?: number,
    public processId?: number,
    public name?: string,
    public runBy?: string,
    public startTime?: Moment,
    public endTime?: Moment,
    public totalObjects?: number,
    public successObjects?: number,
    public errorObjects?: number,
    public status?: string
  ) {}
}
