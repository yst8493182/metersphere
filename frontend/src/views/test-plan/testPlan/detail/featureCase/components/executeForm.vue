<template>
  <a-form :model="form">
    <a-form-item field="lastExecResult" class="mb-[8px]">
      <a-radio-group v-model:model-value="form.lastExecResult">
        <a-radio v-for="item in executionResultList" :key="item.key" :value="item.key">
          <ExecuteResult :execute-result="item.key" />
        </a-radio>
      </a-radio-group>
    </a-form-item>
    <a-form-item field="content" asterisk-position="end" class="mb-0">
      <div class="flex w-full items-center">
        <MsRichText
          v-model:raw="form.content"
          v-model:commentIds="form.commentIds"
          v-model:filedIds="form.planCommentFileIds"
          :upload-image="handleUploadImage"
          :preview-url="`${PreviewEditorImageUrl}/${appStore.currentProjectId}`"
          :auto-height="false"
          class="w-full"
          :max-height="props.richTextMaxHeight"
          :placeholder="
            props.isDblclickPlaceholder
              ? t('testPlan.featureCase.richTextDblclickPlaceholder')
              : t('editor.placeholder')
          "
        />
      </div>
    </a-form-item>
  </a-form>
</template>

<script setup lang="ts">
  import MsRichText from '@/components/pure/ms-rich-text/MsRichText.vue';
  import ExecuteResult from '@/components/business/ms-case-associate/executeResult.vue';

  import { editorUploadFile } from '@/api/modules/case-management/featureCase';
  import { PreviewEditorImageUrl } from '@/api/requrls/case-management/featureCase';
  import { useI18n } from '@/hooks/useI18n';
  import { useAppStore } from '@/store';

  import type { ExecuteFeatureCaseFormParams } from '@/models/testPlan/testPlan';
  import { LastExecuteResults } from '@/enums/caseEnum';

  import { executionResultMap } from '@/views/case-management/caseManagementFeature/components/utils';

  const props = defineProps<{
    isDblclickPlaceholder?: boolean;
    richTextMaxHeight?: string;
  }>();

  const form = defineModel<ExecuteFeatureCaseFormParams>('form', {
    required: true,
  });

  const { t } = useI18n();
  const appStore = useAppStore();

  const executionResultList = computed(() =>
    Object.values(executionResultMap).filter((item) => item.key !== LastExecuteResults.PENDING)
  );

  async function handleUploadImage(file: File) {
    const { data } = await editorUploadFile({
      fileList: [file],
    });
    return data;
  }
</script>

<style lang="less" scoped>
  :deep(.arco-form-item-label-col) {
    display: none;
  }
  :deep(.arco-form-item-wrapper-col) {
    flex: 1;
  }
</style>
