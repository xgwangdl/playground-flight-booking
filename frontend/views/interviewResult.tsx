import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import {useEffect, useState} from "react";
import {AssistantService, BookingService} from "Frontend/generated/endpoints";
import InterViewRecords from "../generated/ai/spring/demo/ai/playground/services/InterViewTools/InterViewRecord";
import {GridColumn} from "@vaadin/react-components/GridColumn";
import {Grid,type GridEventContext} from "@vaadin/react-components/Grid";
import {MessageInput} from "@vaadin/react-components/MessageInput";
import {nanoid} from "nanoid";
import {SplitLayout} from "@vaadin/react-components/SplitLayout";
import Message, {MessageItem} from "../components/Message";
import MessageList from "Frontend/components/MessageList";
import { Button, Notification, TextField } from '@vaadin/react-components';
import { Tooltip } from '@vaadin/react-components/Tooltip.js';

export const config: ViewConfig = { menu: { order: 2, icon: 'line-awesome/svg/globe-solid.svg' }, title: '面试结果' };

export default function InterviewView() {
  const [chatId, setChatId] = useState(nanoid());
  const [working, setWorking] = useState(false);
  const [interViews, setInterView] = useState<InterViewRecord[]>([]);

  useEffect(() => {
    // Update bookings when we have received the full response
    if (!working) {
      BookingService.getInterView().then(setInterView);
    }
  }, [working]);

const statusRenderer = (status: string) => (
      <span {...{ theme: `badge ${status === '淘汰' ? 'error' : 'success'}` }}>
         {status}
      </span>
);

const tooltipGenerator = (context: GridEventContext<interViews>): string => {
  let text = '';

  const { column, item } = context;
  if (column && item && column.path == 'evaluate') {
    text = item.evaluate;
  }

  return text;
};

  return (
    <SplitLayout className="h-full">
      <div className="flex flex-col gap-m p-m box-border" style={{width: '100%'}} >
        <h3>候选者名单</h3>
        <Grid items={interViews} className="flex-shrink-0" theme="row-stripes">
          <GridColumn path="number" header="序号" autoWidth/>
          <GridColumn path="name" header="姓名" autoWidth/>
          <GridColumn path="score" header="得分" autoWidth/>
          <GridColumn header="评价" >
          {({ item: interView }) => statusRenderer(interView.interViewStatus)}
          </GridColumn>
          <GridColumn path="email" header="邮箱" autoWidth/>
          <GridColumn path="evaluate" header="评语" >
          {({ item: interView  }) => (
                <span>
                  {interView.evaluate}
                </span>
              )}
          </GridColumn>
          <GridColumn header="发信" >
                {({ item: interView }) => (
                  <Button
                     onClick={() => {
                              BookingService.sendMail(interView.number,interView.name);
                            }}>
                     ✉
                  </Button>
                )}
          </GridColumn>
          <Tooltip slot="tooltip" generator={tooltipGenerator} />
        </Grid>
      </div>
    </SplitLayout>

  );
}
