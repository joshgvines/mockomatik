package com.mockomatik.service.scan.filter;

import com.mockomatik.service.scan.impl.FileByLineCollection;

import java.io.IOException;

public class CommentFilter {
    public boolean commentFilter(FileByLineCollection fileByLineCollection, String line) throws IOException {
        if (line.contains("/*") || line.contains("/**")) {
            if (!line.contains("*/")) {
                return fileByLineCollection.dropLinesUntilFinds("*/");
            }
            extractAndRemoveInlineComment(line);
        }
        return line.contains(" //");
    }

    // TODO: This is an experiment
    private void extractAndRemoveInlineComment(String line) {
        boolean hasCommentSyntax = true;
        while(hasCommentSyntax) {
            if (!line.contains("*/") || !line.contains("/*")) {
                // Remove any leftover comment syntax chars
                line = line.replace("*/", "");
                line = line.replace("/*", "");
                line = line.replace("*", "");
                hasCommentSyntax = false;
            } else {
                int firstCommentIndex = line.indexOf("/*");
                int secondCommentIndex = line.lastIndexOf("*/") + 2;
                if (commentIndexCheck(firstCommentIndex, secondCommentIndex, line)) {
                    String removeComment = line.substring(firstCommentIndex, secondCommentIndex);
                    line = line.replace(removeComment, "");
                }
                firstCommentIndex = line.indexOf("*/");
                secondCommentIndex = line.lastIndexOf("/*") + 2;
                if (commentIndexCheck(firstCommentIndex, secondCommentIndex, line)) {
                    String removeComment = line.substring(firstCommentIndex, secondCommentIndex);
                    line = line.replace(removeComment, "");
                }
            }
        }
    }

    private boolean commentIndexCheck(int firstCommentIndex, int secondCommentIndex, String line) {
        return (firstCommentIndex >= 0)
                && (firstCommentIndex < secondCommentIndex)
                && (secondCommentIndex < line.length());
    }
}
